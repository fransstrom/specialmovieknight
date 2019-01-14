import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Modal from '@material-ui/core/Modal';
import Button from '@material-ui/core/Button';
import Divider from "@material-ui/core/Divider/Divider";
import '../css/modal.css';

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
        console.log(eStart, eEnd, title, id)
    }

    render() {
        const { classes, bookingsElem, item } = this.props;

        var freebookings = bookingsElem;
        freebookings.sort((a, b) =>{
            return a.startMillis - b.startMillis;
        });

        var freebookingsElem = freebookings.map(e => {
            var sDate = new Date(e.startMillis).toString().slice(0, 25);
            var eDate = new Date(e.endMillis).toString().slice(15, 25);
            return (
                <li value={e} onClick={() => this.handleBooking(e.startMillis, e.endMillis, item.title, item.id)} key={sDate + '' + eDate}>
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
            </div>
        );
    }
}

ClientBookingModalClass.propTypes = {
    classes: PropTypes.object.isRequired,
};

// We need an intermediary variable for handling the recursive nesting.
const ClientBookingModal = withStyles(styles)(ClientBookingModalClass);

export default ClientBookingModal;