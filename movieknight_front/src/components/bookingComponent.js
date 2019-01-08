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
      </div>
    );
  }
}
