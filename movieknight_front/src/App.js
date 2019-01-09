import React, { Component } from 'react';
import './App.css';
import './components/admin/adminGridComponent';
import './components/admin/adminCardComponent';
import axios from 'axios';

import CenteredGrid from "./components/admin/adminGridComponent";
import ClientCenteredGrid from "./components/client/clientGridComponent";
import PrimarySearchAppBar from "./components/admin/adminNavbarComponent";
import ClientSearchAppBar from "./components/client/clientNavbarComponent";
import Booking from './components/bookingComponent'

class App extends Component {

    constructor(props){
        super();
        this.state = {
            adminState: false,
            movieList: [],
            movieInfo: {},
            moviesFromDatabase: [],
            dates:[]
        }
        this.getAllMoviesFromDatabase();
    }

    componentDidMount(){
            //triggar refreshtoken i backend
    let url = 'http://localhost:6969/events';
    axios.get(url).then(res=>{
      this.setState({dates:res.data})
    });
    }

    search = (query) => {
        let url="http://localhost:6969/omdb/movies/search/?s="+query;
        axios.get(url)
            .then(res => {
                const movieList = res.data;
                if(movieList.Search!=null) {
                    this.setState({movieList: movieList.Search});
                }else{
                    this.setState({movieList: []})
                }
            })
    }

    getMovieInfo = (query) =>{
        this.setState({movieInfo: {}})
        let url="http://localhost:6969/omdb/movies/get/?s="+query;
        axios.get(url)
            .then(res => {
                const movieInfo = res.data;
                this.setState({movieInfo: movieInfo});
            });
    }

    getAllMoviesFromDatabase = () =>{
        let url="http://localhost:6969/getAllMovies";
        axios.get(url)
            .then(res => {
                const allmovies = res.data;
                console.log(allmovies);
                this.setState({moviesFromDatabase: allmovies});
            });
    }

    async addToDataBase(query) {
        let url = "http://localhost:6969/omdb/movies/get/?s=" + query;
        await axios.get(url)
            .then(res => {
                const movieInfo = res.data;
                this.setState({movieInfo: movieInfo});
            })

        let urlForPost = "http://localhost:6969/admin/addMovieToDatabase2/?id=" + this.state.movieInfo.imdbID;
        axios.post(urlForPost, {
        })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
        console.log("Added " + this.state.movieInfo.Title + " " + this.state.movieInfo.imdbID + " To DataBase");
    }

    render() {
        console.log(this.state.dates)
        if (this.state.adminState) {
            return (
                <div className="App">
                    <button onClick={()=> {this.setState({adminState: false})}}>Click</button>
                    <header className="App-header">
                        MovieNight Admin Page
                        {this.props.movieSearch}
                    </header>
                    <nav className="App-nav">
                        <PrimarySearchAppBar searchMovie={this.search.bind(this)}/>
                    </nav>
                    <div className="App-body">
                        <CenteredGrid movieListFromAPI={this.state.movieList} movieInfoFromAPI={this.state.movieInfo}
                                      getMovieInfo={this.getMovieInfo.bind(this)}
                                      addToDataBase={this.addToDataBase.bind(this)}/>
                    </div>
                   
                </div>
            );
        }
        else if (!this.state.adminState) {
            return (
                <div className="App">
                    <button onClick={()=> {this.setState({adminState: true})}}>Click</button>
                    <header className="App-header-client">
                        MovieNight
                        {this.props.movieSearch}
                    </header>
                    <nav className="App-nav">
                        <ClientSearchAppBar searchMovie={this.search.bind(this)}/>
                    </nav>
                    <div className="App-body">
                        <ClientCenteredGrid allMoviesFromDatabase={this.state.moviesFromDatabase}
                                      getAllMoviesFromDatabase={this.getAllMoviesFromDatabase.bind(this)}
                                      />


                    </div>
                    <div>
                    <Booking dates={this.state.dates}></Booking>
                    </div>
                    
                </div>
            );
        }
    }

}

export default App;