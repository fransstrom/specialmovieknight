import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import '../admin/adminCardComponent';
import ClientImgMediaCard from "./clientCardComponent";

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


function ClientCenteredGrid(props) {
    const { classes, allMoviesFromDatabase, getAllMoviesFromDatabase } = props;
    return (

        <div className={classes.root}>
            <Grid container spacing={24}>
                {allMoviesFromDatabase.map((item, index) => {
                    return(
                        <Grid item xs={3} key={item.imdbID}>
                            <Paper className={classes.paper}>
                                <ClientImgMediaCard item={item} index={index}/>
                            </Paper>
                        </Grid>
                    )
                })}
            </Grid>
        </div>
    );
}

ClientCenteredGrid.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ClientCenteredGrid);