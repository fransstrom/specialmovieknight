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

class SimpleModal extends React.Component {
    state = {
        open: false,
    };

    handleButtonClick = () => {
        this.props.getMovieInfo(this.props.item.imdbID);
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
        const { classes, movieInfoFromAPI } = this.props;

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
                            <span className={"bold"}>{movieInfoFromAPI.Title}</span>
                        </Typography>
                        <Divider variant="middle" />
                        <Typography variant="subtitle1" id="simple-modal-description">
                            <span className={"bold"}>Imdb Rating:</span> {movieInfoFromAPI.imdbRating}
                            <Divider variant="middle" />
                            <span className={"bold"}>Imdb ID:</span> {movieInfoFromAPI.imdbID}
                            <Divider variant="middle" />
                            <span className={"bold"}>Released:</span> {movieInfoFromAPI.Released}
                            <Divider variant="middle" />
                            <span className={"bold"}>Runtime:</span> {movieInfoFromAPI.Runtime}
                            <Divider variant="middle" />
                            <span className={"bold"}>Rated:</span> {movieInfoFromAPI.Rated}
                            <Divider variant="middle" />
                            <span className={"bold"}>Actors:</span> {movieInfoFromAPI.Actors}
                            <Divider variant="middle" />
                            <span className={"bold"}>Director:</span> {movieInfoFromAPI.Director}
                            <Divider variant="middle" />
                            <span className={"bold"}>Genre:</span> {movieInfoFromAPI.Genre}
                            <Divider variant="middle" />
                            <span className={"bold"}>Plot:</span> {movieInfoFromAPI.Plot}
                            <Divider variant="middle" />
                            <span className={"bold"}>Awards:</span> {movieInfoFromAPI.Awards}
                        </Typography>
                    </div>
                </Modal>
            </div>
        );
    }
}

SimpleModal.propTypes = {
    classes: PropTypes.object.isRequired,
};

// We need an intermediary variable for handling the recursive nesting.
const SimpleModalWrapped = withStyles(styles)(SimpleModal);

export default SimpleModalWrapped;