import React, { Component } from 'react';
import './App.css';
import './components/adminGridComponent';
import './components/adminCardComponent';
import axios from 'axios';

import CenteredGrid from "./components/adminGridComponent";
import PrimarySearchAppBar from "./components/adminNavbarComponent";
import ClientSearchAppBar from "./components/clientNavbarComponent";
import Booking from './components/bookingComponent'

class App extends Component {

    constructor(props){
        super();
        this.state = {
            adminState: false,
            movieList: [],
            movieInfo: {}
        }
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

    async addToDataBase(query) {
        let url = "http://localhost:6969/omdb/movies/get/?s=" + query;
        await axios.get(url)
            .then(res => {
                const movieInfo = res.data;
                this.setState({movieInfo: movieInfo});
            })
        console.log("Added " + this.state.movieInfo.Title + " To DataBase");
    }

    render() {
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
                        <CenteredGrid movieListFromAPI={this.state.movieList} movieInfoFromAPI={this.state.movieInfo}
                                      getMovieInfo={this.getMovieInfo.bind(this)}
                                      addToDataBase={this.addToDataBase.bind(this)}/>

                                      
                    </div>
                    <div>
                    <Booking></Booking>
                    </div>
                    
                </div>
            );
        }
    }

}

export default App;