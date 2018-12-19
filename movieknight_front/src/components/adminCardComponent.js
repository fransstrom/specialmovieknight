import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import SimpleModalWrapped from './adminModalComponent';

const styles = {
    card: {
      //  maxWidth: 345,
    },
    media: {
        // ⚠️ object-fit is not supported by IE 11.
        objectFit: 'cover',
    },
};

function ImgMediaCard(props) {
    const { classes, item, getMovieInfo, movieInfoFromAPI, addToDataBase } = props;
    return (
        <Card className={classes.card}>
            <CardActionArea>
                <CardMedia
                    component="img"
                    alt="Picture not found"
                    className={classes.media}
                    height="140"

                    image={item.Poster}
                    title="Bild"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {item.Title}
                    </Typography>
                    <Typography component="p">
                        {item.Year}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <SimpleModalWrapped getMovieInfo={getMovieInfo} item={item} movieInfoFromAPI={movieInfoFromAPI}/>
                <Button onClick={() => {addToDataBase(item.imdbID)}} size="small" color="primary">
                    Add to database
                </Button>
            </CardActions>
        </Card>
    );
}

ImgMediaCard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ImgMediaCard);