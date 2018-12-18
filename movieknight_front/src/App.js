import React, { Component } from 'react';
import './App.css';
import './components/gridComponent';
import './components/cardComponent';
import axios from 'axios';
import CenteredGrid from './components/gridComponent';
import PrimarySearchAppBar from './components/navbarComponent';


class App extends Component {

    constructor(props){
        super();
        this.state = {
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

    getMovieInfo = (query) => {
        let url="http://localhost:6969/omdb/movies/get/?s="+query;
        axios.get(url)
            .then(res => {
                const movieInfo = res.data;
                console.log(movieInfo)
                this.setState({movieInfo: movieInfo});
            })
    }

    render() {

        return (
            <div className="App">
                <header className="App-header">
                    Movie (Frickin) Night
                    {this.props.movieSearch}
                </header>
                <nav className="App-nav">
                    <PrimarySearchAppBar searchMovie={this.search.bind(this)}/>
                </nav>
                <div className="App-body">
                    <CenteredGrid movieListFromAPI={this.state.movieList} movieInfoFromAPI={this.state.movieInfo} getMovieInfo={this.getMovieInfo.bind(this)}/>
                </div>
                {/*<footer className="App-footer">
                Footer
                </footer>*/}
            </div>
        );
    }

}

export default App;