import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Modal from '@material-ui/core/Modal';
import Button from '@material-ui/core/Button';
import Divider from "@material-ui/core/Divider/Divider";
import '../css/modal.css';
import axios from 'axios';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.min.css';
import { toast } from 'react-toastify';

function getModalStyle() {
    const top = 50;
    const left = 50;

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const styles = theme => ({
    paper: {
        position: 'absolute',
        width: theme.spacing.unit * 50,
        backgroundColor: theme.palette.background.paper,
        boxShadow: theme.shadows[5],
        padding: theme.spacing.unit * 4,
    },
});

class ClientBookingModalClass extends React.Component {
    state = {
        open: false,
    };

    handleButtonClick = () => {
        this.handleOpen();
    }

    handleOpen = () => {
        setTimeout(
            function() {
                this.setState({open: true});
            }
                .bind(this),
            150
        );
    };

    handleClose = () => {
        this.setState({ open: false });
    };

    handleBooking = (eStart, eEnd, title, id) => {
        this.handleClose();
        axios.post(`http://localhost:6969/booking`, {
            startDate:eStart,
            endDate:eEnd,
            movieTitle:title,
            movieId:id
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          });
        toast.success('🍿 Movie Added 🥤', {
            position: "bottom-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true
        });

    }

    render() {
        const { classes, bookingsElem, item, updateBookingAndAvailableTimes } = this.props;

        var freebookings = bookingsElem;
        freebookings.sort((a, b) =>{
            return a.startMillis - b.startMillis;
        });

        var freebookingsElem = freebookings.map(e => {
            var sDate = new Date(e.startMillis).toString().slice(0, 25);
            var eDate = new Date(e.endMillis).toString().slice(15, 25);
            return (
                <li key={sDate+eDate} className="bookingListItem" value={e} onClick={() => {this.handleBooking(e.startMillis, e.endMillis, item.title, item.id); updateBookingAndAvailableTimes();}} >
                    {sDate + ' - '}
                    {eDate}{' '}
                </li>
            );
        });



        return (
            <div>
                <Button onClick={this.handleButtonClick}>Book Movie</Button>
                <Modal
                    aria-labelledby="simple-modal-title"
                    aria-describedby="simple-modal-description"
                    open={this.state.open}
                    onClose={this.handleClose}
                >
                    <div style={getModalStyle()} className={classes.paper}>

                        <Typography variant="h6" id="modal-title">
                            <span className={"bold"}>Available times</span>
                        </Typography>
                        <Divider variant="middle" />
                        <Typography variant="subtitle1" id="simple-modal-description">
                            <ul>{freebookingsElem}</ul>
                        </Typography>
                    </div>
                </Modal>
                <ToastContainer
                    position="bottom-center"
                    autoClose={5000}
                    hideProgressBar={false}
                    newestOnTop={false}
                    closeOnClick
                    rtl={false}
                    pauseOnVisibilityChange
                    draggable
                    pauseOnHover
                />            </div>
        );
    }
}

ClientBookingModalClass.propTypes = {
    classes: PropTypes.object.isRequired,
};

// We need an intermediary variable for handling the recursive nesting.
const ClientBookingModal = withStyles(styles)(ClientBookingModalClass);

export default ClientBookingModal;