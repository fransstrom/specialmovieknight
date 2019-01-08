import React, { Component } from 'react';
import axios from 'axios';
export default class bookingComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dates: []
    };
  }

  componentDidMount() {
    let url = 'http://localhost:6969/getdates';
    axios.get(url).then(res => {
      const dates = res.data;
      if (dates != null) {
        this.setState({ dates });
      } else {
        this.setState({ dates: [] });
      }
    });
  }

  render() {
    let unavailableDates = this.state.dates.map(e => {
      return (
        <li key={e.id}>
          {e.startDateTime.substring(0, 16).replace('T', ' ')} -{' '}
          {e.endDateTime.substring(0, 16).replace('T', ' ')}
        </li>
      );
    });
    console.log(this.state);
    return (
      <div>
        <p>booking</p>
        <ul>{unavailableDates}</ul>
        <iframe src="https://calendar.google.com/calendar/embed?src=ij62rf997musf786sqmdr0e9a8%40group.calendar.google.com&ctz=Europe%2FStockholm" style={{border: 0}} width={800} height={600} frameBorder={0} scrolling="no" title="calendar">
      </iframe>
      </div>
    );
  }
}
