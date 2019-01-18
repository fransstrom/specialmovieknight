import React, { Component } from 'react';
import './App.css';
import './components/admin/adminGridComponent';
import './components/admin/adminCardComponent';
import axios from 'axios';

import CenteredGrid from './components/admin/adminGridComponent';
import ClientCenteredGrid from './components/client/clientGridComponent';
import PrimarySearchAppBar from './components/admin/adminNavbarComponent';
import ClientSearchAppBar from './components/client/clientNavbarComponent';

import BookingsComponent from './components/client/bookingsComponent';
import Button from '@material-ui/core/Button';
//Kevin is genius
class App extends Component {
  constructor(props) {
    super();
    this.state = {
      adminState: false,
      movieList: [],
      movieInfo: {},
      moviesFromDatabase: [],
      availableBookingTimes: [],
      bookings: []
    };
    console.log('Constructor');
    this.getAllMoviesFromDatabase();
    this.updateBookingAndAvailableTimes = this.updateBookingAndAvailableTimes.bind(
      this
    );
  }

   async componentDidMount() {
   await this.updateBookingAndAvailableTimes();
  }
 

  async updateBookingAndAvailableTimes() {
    console.log('UPDATED');
    await axios.get('http://localhost:6969/events').then(res => {
      if (res.data) {
        this.setState({ availableBookingTimes: res.data });
      } else {
        this.setState({ availableBookingTimes: [] });
      }
    }).then();

    await axios
      .get('http://localhost:6969/bookings')
      .then(res => {
        if (res.data) {
          this.setState({ bookings: res.data });
        } else {
          this.setState({ bookings: [] });
        }
      })
      .then(() => console.log(this.state));
  }

  handleAdminState = () => {
    this.setState({ adminState: !this.state.adminState });
    this.getAllMoviesFromDatabase();
  };

  search = query => {
    let url = 'http://localhost:6969/omdb/movies/search/?s=' + query;
    axios.get(url).then(res => {
      const movieList = res.data;
      if (movieList.Search != null) {
        this.setState({ movieList: movieList.Search });
      } else {
        this.setState({ movieList: [] });
      }
    });
  };

  getMovieInfo = query => {
    this.setState({ movieInfo: {} });
    let url = 'http://localhost:6969/omdb/movies/get/?s=' + query;
    axios.get(url).then(res => {
      const movieInfo = res.data;
      this.setState({ movieInfo: movieInfo });
    });
  };

  getAllMoviesFromDatabase = () => {
    let url = 'http://localhost:6969/getAllMovies';
    axios.get(url).then(res => {
      const allmovies = res.data;
      console.log(allmovies);
      this.setState({ moviesFromDatabase: allmovies });
    });
  };

  async addToDataBase(query) {
    let url = 'http://localhost:6969/omdb/movies/get/?s=' + query;
    await axios.get(url).then(res => {
      const movieInfo = res.data;
      this.setState({ movieInfo: movieInfo });
    });

    let urlForPost =
      'http://localhost:6969/admin/addMovieToDatabase2/?id=' +
      this.state.movieInfo.imdbID;
    axios
      .post(urlForPost, {})
      .then(function(response) {
        console.log(response);
      })
      .catch(function(error) {
        console.log(error);
      });
    console.log(
      'Added ' +
        this.state.movieInfo.Title +
        ' ' +
        this.state.movieInfo.imdbID +
        ' To DataBase'
    );
  }

  render() {
    if (this.state.adminState) {
      return (
        <div className="App">
       
          <header className="App-header">
            MovieNight Admin Page
            {this.props.movieSearch}
          </header>
          <nav className="App-nav">
            <PrimarySearchAppBar searchMovie={this.search.bind(this)} />
          </nav>
          <div className="App-body">
            <CenteredGrid
              movieListFromAPI={this.state.movieList}
              movieInfoFromAPI={this.state.movieInfo}
              getMovieInfo={this.getMovieInfo.bind(this)}
              addToDataBase={this.addToDataBase.bind(this)}
            />
          </div>
          <Button
            onClick={() => {
              this.handleAdminState();
            }}>
            Click
          </Button>
        </div>
      );
    } else if (!this.state.adminState) {
      return (
        <div className="App">
            
          <header className="App-header-client">
            <h1 className="mainLabel">MovieNight</h1>
            {this.props.movieSearch}
          </header>
          <nav className="App-nav">
            <ClientSearchAppBar searchMovie={this.search.bind(this)} />
          </nav>
          <div className="App-body">
            <ClientCenteredGrid
              allMoviesFromDatabase={this.state.moviesFromDatabase}
              bookingsElem={this.state.availableBookingTimes}
              updateBookingAndAvailableTimes={
                this.updateBookingAndAvailableTimes
              }
              getAllMoviesFromDatabase={this.getAllMoviesFromDatabase.bind(
                this
              )}
            />
          </div>
          <div className="addedBookings">
            <h2>Bookings</h2>
            <BookingsComponent
              updateBookingAndAvailableTimes={
                this.updateBookingAndAvailableTimes
              }
              bookings={this.state.bookings}
            />
          </div>
          <Button
            onClick={() => {
              this.handleAdminState();
            }}>
            Click
          </Button>
        </div>
      );
    }
  }
}

export default App;
