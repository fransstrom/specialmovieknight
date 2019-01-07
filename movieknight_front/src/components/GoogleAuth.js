import React, { Component } from 'react';
import Button from '@material-ui/core/Button';

export default class GoogleAuth extends Component {
  state = {
    isSignedIn: null,
    userName: null
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
          let user = auth.currentUser.Ab.w3;
          let userName=null;
          if (user === undefined) {
            user = null;
          } else {
          userName=user.ig;
          }
          this.setState({
            isSignedIn: auth.isSignedIn.get(),
            userName: userName
          });
          auth.isSignedIn.listen(this.onAuthChange);
        }).then(this.signInCallback);
    });
    
  }

  signInCallback = authResult => {
    
    if (authResult['code']) {
      // Send the code to the server
      window.$.ajax({
        type: 'POST',
        url: 'http://localhost:6969/google',
        // Always include an `X-Requested-With` header in every AJAX request,
        // to protect against CSRF attacks.
        headers: {
          'X-Requested-With': 'XMLHttpRequest'
        },
        contentType: 'application/octet-stream; charset=utf-8',
        success: function(result) {
          // Handle or verify the server response.
        
        },
        processData: false,
        data: authResult['code']
      });
    } else {
      // There was an error.
      // Get refreshtoken from database user id
     let userId= window.gapi.auth2.getAuthInstance().currentUser.Ab.El;
     console.log(userId)
    }

    
  };

  onAuthChange = () => {
    this.setState({
      isSignedIn: window.gapi.auth2.getAuthInstance().isSignedIn.get(),
      userName: window.gapi.auth2.getAuthInstance().currentUser.Ab.w3.ig
    });
  };

  onSignIn = () => {
    window.gapi.auth2
      .getAuthInstance()
      .grantOfflineAccess( {scope: 'https://www.googleapis.com/auth/calendar'})
      .then(this.signInCallback);
  };

  onSignOut = () => {
    window.gapi.auth2.getAuthInstance().signOut();
  };

  renderAuthButoon = () => {
    if (this.state.isSignedIn === null) {
      return null;
    }
    if (this.state.isSignedIn) {
      return (
        <Button onClick={this.onSignOut} variant="contained" color="secondary">
          <i className="fab fa-google" /> Sign out {this.state.userName}
        </Button>
      );
    } else {
      return (
        <Button onClick={this.onSignIn} variant="contained" color="secondary">
          <i className="fab fa-google" /> Sign in
        </Button>
      );
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
