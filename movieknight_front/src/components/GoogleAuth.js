import React, { Component } from 'react';
import Button from '@material-ui/core/Button';

export default class GoogleAuth extends Component {

  state = {
    isSignedIn: null
  };

  componentDidMount() {
    window.gapi.load('client:auth2', () => {
      window.gapi.client
        .init({
          client_id:
            '892035413711-k2fuimcicp4rkrp36auu2qt56kirnl12.apps.googleusercontent.com',
          // Scopes to request in addition to 'profile' and 'email'
          scope: 'https://www.googleapis.com/auth/calendar'
        })
        .then(() => {
          const auth = window.gapi.auth2.getAuthInstance();
          this.setState({
            isSignedIn: auth.isSignedIn.get()
          });
          auth.isSignedIn.listen(this.onAuthChange);
        });
    });
  }

  onAuthChange=()=>{
      this.setState({
          isSignedIn:window.gapi.auth2.getAuthInstance().isSignedIn.get()
      })
  }

onSignIn=()=>{
    window.gapi.auth2.getAuthInstance().signIn();
}

onSignOut=()=>{
    window.gapi.auth2.getAuthInstance().signOut();
}


  renderAuthButoon = () => {
    if (this.state.isSignedIn === null) {
      return null;
    }
    if (this.state.isSignedIn) {
      return <Button onClick={this.onSignOut} variant="contained" color="secondary"><i className="fab fa-google"></i>{" "}Sign out</Button>;
    } else {
      return <Button onClick={this.onSignIn} variant="contained" color="secondary" ><i className="fab fa-google"></i> {" "}Sign in</Button>;
    }
  };

  render() {
    return (
      <div>
        <div>{this.renderAuthButoon()}</div>
      </div>
    );
  }
}
