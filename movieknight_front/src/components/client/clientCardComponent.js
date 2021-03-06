import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';

import Typography from '@material-ui/core/Typography';
import ClientSimpleModalWrapped from './clientModalComponent';
import ClientBookingModal from './clientBookingModal';

const styles = {
    card: {
      //  maxWidth: 345,
    },
    media: {
        // ⚠️ object-fit is not supported by IE 11.
        objectFit: 'cover',
    },
};

function ClientImgMediaCard(props) {
    const { classes, item, bookingsElem, updateBookingAndAvailableTimes } = props;
    return (
        <Card className={classes.card}>
            <CardActionArea>
                <CardMedia
                    component="img"
                    alt="Picture not found"
                    className={classes.media}
                    height="140"

                    image={item.posterUrl}
                    title="Bild"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                        {item.title}
                    </Typography>
                    <Typography component="p">
                        {item.releaseDate}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions>
                <ClientSimpleModalWrapped  item={item} />
                <ClientBookingModal bookingsElem={bookingsElem} item={item} updateBookingAndAvailableTimes={updateBookingAndAvailableTimes}/>
            </CardActions>
        </Card>
    );
}

ClientImgMediaCard.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(ClientImgMediaCard);