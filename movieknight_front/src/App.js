import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import './components/gridComponent';
import './components/cardComponent';
import CenteredGrid from "./components/gridComponent";
import axios from 'axios';

class App extends Component {
    constructor(props){
        super();
        this.state = {movieList: []}
    }

    loadData =(url) =>{
        axios.get(url)
            .then(res => {
                const movieList = res.data;
                this.setState({ movieList: movieList.Search});
            })
    }


  render() {
        this.loadData("http://localhost:6969/omdb/movies/search/");

    return (
      <div className="App">
        <header className="App-header">
          {/*<img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a className="App-link" href="https://reactjs.org" target="_blank" rel="noopener noreferrer">
            Learn React
          </a>*/}
          Header
            {this.props.movieSearch}
        </header>
          <nav className="App-nav">
              Navbar
          </nav>
          <div className="App-body">
              <CenteredGrid movieListFromAPI={this.state.movieList}/>
          </div>
          <footer className="App-footer">
              Footer
          </footer>

      </div>
    );
  }
}

export default App;
