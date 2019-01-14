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

class ClientSimpleModal extends React.Component {
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

    render() {
        const { classes, item } = this.props;

        return (
            <div>
                <Button onClick={this.handleButtonClick}>Details</Button>
                <Modal
                    aria-labelledby="simple-modal-title"
                    aria-describedby="simple-modal-description"
                    open={this.state.open}
                    onClose={this.handleClose}
                >
                    <div style={getModalStyle()} className={classes.paper}>
                        <Typography variant="h6" id="modal-title">
                            <span className={"bold"}>{item.title}</span>
                        </Typography>
                        <Divider variant="middle" />
                        <Typography variant="subtitle1" id="simple-modal-description">
                            <span className={"bold"}>Imdb Rating:</span> {item.imdbRating}
                            <Divider variant="middle" />
                            <span className={"bold"}>Imdb ID:</span> {item.id}
                            <Divider variant="middle" />
                            <span className={"bold"}>Released:</span> {item.releaseDate}
                            <Divider variant="middle" />
                            <span className={"bold"}>Runtime:</span> {item.duration}
                            <Divider variant="middle" />
                            <span className={"bold"}>Actors:</span> {item.actors}
                            <Divider variant="middle" />
                            <span className={"bold"}>Director:</span> {item.directors}
                            <Divider variant="middle" />
                            <span className={"bold"}>Genre:</span> {item.genre}
                            <Divider variant="middle" />
                            <span className={"bold"}>Plot:</span> {item.description}
                        </Typography>
                    </div>
                </Modal>
            </div>
        );
    }
}

ClientSimpleModal.propTypes = {
    classes: PropTypes.object.isRequired,
};

// We need an intermediary variable for handling the recursive nesting.
const ClientSimpleModalWrapped = withStyles(styles)(ClientSimpleModal);

export default ClientSimpleModalWrapped;