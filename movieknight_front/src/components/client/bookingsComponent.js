import React, { Component } from 'react'
import Button from '@material-ui/core/Button';

const BookingsComponent=({bookings})=>{

 return bookings.map(e=>{
    var start =new Date(parseInt(e.startDate)).toString().slice(0, 25);
    var end =new Date(parseInt(e.endDate)).toString().slice(15, 25);
    return<li key={e.id}>{e.movieTitle+" "}{start+" - "}{end}<Button>Ta bort</Button></li>
})
}

export default BookingsComponent