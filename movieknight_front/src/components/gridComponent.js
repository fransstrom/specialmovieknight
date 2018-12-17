import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import './cardComponent';
import ImgMediaCard from "./cardComponent";

let movie={title: "FILM 1", picture: "https://img.itch.zone/aW1nLzcyNzgyMy5wbmc=/original/D%2Fl6j%2B.png", rating: 5, plot: "bad"}
let movie2={title: "Kevin 2", picture: "https://img.itch.zone/aW1nLzcyNzgyMy5wbmc=/original/D%2Fl6j%2B.png", rating: 5, plot: "bad"}
let movie3={title: "Derp 3", picture: "https://img.itch.zone/aW1nLzcyNzgyMy5wbmc=/original/D%2Fl6j%2B.png", rating: 5, plot: "bad"}
let movie4={title: "Nope 4", picture: "https://img.itch.zone/aW1nLzcyNzgyMy5wbmc=/original/D%2Fl6j%2B.png", rating: 5, plot: "bad"}

export let movieList=[movie, movie2, movie3, movie4];

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing.unit * 0,
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
});


function CenteredGrid(props) {
    const { classes, movieListFromAPI } = props;
    console.log(movieListFromAPI)
    return (

        <div className={classes.root}>
            <Grid container spacing={24}>
                {movieListFromAPI.map((item, index) => {
                    return(
                        <Grid item xs={3} key={index}>
                            <Paper className={classes.paper}>
                                <ImgMediaCard item={item} index={index}/>
                            </Paper>
                        </Grid>
                    )
                })}
            </Grid>
        </div>
    );
}

CenteredGrid.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CenteredGrid);