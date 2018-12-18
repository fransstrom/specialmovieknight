import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import './cardComponent';
import ImgMediaCard from "./cardComponent";

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