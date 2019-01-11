import React, { Component } from 'react';
export default class bookingComponent extends Component {
  // eslint-disable-next-line no-useless-constructor
  constructor(props) {
    super(props);
  }

  componentDidMount() {}

  render() {
    console.log(this.props);
    let unavailableDates = this.props.freeTimes.map((e, index) => {
      return (
        <li key={index}>
        {e.date + ': '}  {e.start_time + ' '}-{' ' + e.end_time}
        </li>
      );
    });
    return (
      <div>
        <p>Available time to book</p>
        <ul>{unavailableDates}</ul>
      </div>
    );
  }
}
