import React, { Component } from 'react';
export default class bookingComponent extends Component {
                 // eslint-disable-next-line no-useless-constructor
                 constructor(props) {
                   super(props);
                 }

                 componentDidMount() {}

                 render() {
                   console.log(this.props.dates);
                   let unavailableDates = this.props.dates.map(
                     (e, index) => {
                       return (
                         <li key={index}>
                           {e.start + ' '}-{' ' + e.end}
                         </li>
                       );
                     }
                   );
                   return <div>
                       <p>booking</p>
                       <ul>{unavailableDates}</ul>
                     </div>;
                 }
               }
