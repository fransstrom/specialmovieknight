import React, { Component } from 'react'
import axios from 'axios';
export default class bookingComponent extends Component {

    constructor(props){
        super(props) 
        this.state={
            dates:[]
        }
    }
    
    componentDidMount(){
        let url="http://localhost:6969/getdates";
        axios.get(url)
            .then(res => {
                const dates = res.data;
                if(dates!=null) {
                    this.setState({dates});
                }else{
                    this.setState({dates: []})
                }
            })
    }
 
  render() {
      console.log(this.state)
    return (
      <div>
        <p>booking</p>
      </div>
    )
  }
}
