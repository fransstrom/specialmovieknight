import React, { Component } from 'react';
import './App.css';
import './components/admin/adminGridComponent';
import './components/admin/adminCardComponent';
import axios from 'axios';

import CenteredGrid from './components/admin/adminGridComponent';
import ClientCenteredGrid from './components/client/clientGridComponent';
import PrimarySearchAppBar from './components/admin/adminNavbarComponent';
import ClientSearchAppBar from './components/client/clientNavbarComponent';
import Booking from './components/bookingComponent';
import Dates, { meeting } from './Dates';
//Kevin is genius
class App extends Component {
  constructor(props) {
    super();
    this.state = {
      adminState: false,
      movieList: [],
      movieInfo: {},
      moviesFromDatabase: [],
      dates: []
    };
    console.log('Constructor');
    this.getAllMoviesFromDatabase();
  }

  componentDidMount() {
    //triggar refreshtoken i backend
    let url = 'http://localhost:6969/events';
    axios.get(url).then(res => {
      this.setState({ dates: res.data });
    });
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
    console.log(this.state.dates);
    var meetings = [];
    this.state.dates.map((e, index) => {
      var startTime = e.startDate.slice(11, 19);
      var endTime = e.endDate.slice(11, 19);

      

      var date=e.startDate.slice(0,10)
      var index = index;
      console.log(startTime + " - " + endTime+ "  "+date)
      
      console.log(new meeting('meeting' + index + '', startTime, endTime, date));
      meetings.push(new meeting('meeting' + index + '', startTime, endTime, date));
    });

    meetings = meetings.getFreeTime();
    console.log(meetings);

    if (this.state.adminState) {
      return (
        <div className="App">
          <button
            onClick={() => {
              this.handleAdminState();
            }}>
            Click
          </button>
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
        </div>
      );
    } else if (!this.state.adminState) {
      return (
        <div className="App">
          <button
            onClick={() => {
              this.handleAdminState();
            }}>
            Click
          </button>
          <header className="App-header-client">
            MovieNight
            {this.props.movieSearch}
          </header>
          <nav className="App-nav">
            <ClientSearchAppBar searchMovie={this.search.bind(this)} />
          </nav>
          <div className="App-body">
            <ClientCenteredGrid
              allMoviesFromDatabase={this.state.moviesFromDatabase}
              getAllMoviesFromDatabase={this.getAllMoviesFromDatabase.bind(
                this
              )}
            />
          </div>
          <div>
            <Booking dates={this.state.dates} />
          </div>
        </div>
      );
    }
  }
}

export default App;
