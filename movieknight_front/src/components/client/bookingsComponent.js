import React, { Component } from 'react';
import Button from '@material-ui/core/Button';
import axios from 'axios';
export default class BookingsComponent extends Component {
 
   async delteBooking (e){
    console.log(e);
    await axios.delete('http://localhost:6969/deletebooking', {
      params: { id: e.id }
    }).then(this.props.updateBookingAndAvailableTimes());
  };

  render() {
    var { bookings } = this.props;

    const BookingsComponent = bookings.map(e => {
      var start = new Date(parseInt(e.startDate)).toString().slice(0, 25);
      var end = new Date(parseInt(e.endDate)).toString().slice(15, 25);
      return (
        <li key={e.id}>
          {e.movieTitle + ' '}
          {start + ' - '}
          {end}
          <Button className="deleteBooking"
            onClick={() => {
              this.delteBooking(e);
            }}>
            Ta bort
          </Button>
        </li>
      );
    });

    return <div>{BookingsComponent}</div>;
  }
}
