import React from 'react';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import InputBase from '@material-ui/core/InputBase';
import { fade } from '@material-ui/core/styles/colorManipulator';
import { withStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import '../css/navbar.css';
import GoogleAuth from "../GoogleAuth";

const styles = theme => ({
  root: {
    width: '100%'
  },

  button: {
    marginLeft: 0
  },

  title: {
    marginRight: 15,
    marginLeft: 15,
    display: 'none',
    [theme.breakpoints.up('sm')]: {
      display: 'block'
    }
  },
  search: {
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25)
    },
    marginRight: theme.spacing.unit * 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing.unit * 0,
      width: 'auto'
    }
  },
  searchIcon: {
    width: theme.spacing.unit * 9,
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  },
  inputRoot: {
    color: 'inherit',
    width: '100%'
  },
  inputInput: {
    paddingTop: theme.spacing.unit,
    paddingRight: theme.spacing.unit,
    paddingBottom: theme.spacing.unit,
    paddingLeft: theme.spacing.unit * 10,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: 200
    }
  }
});

const divStyle = {
  backgroundColor: 'red'
};

class ClientSearchAppBar extends React.Component {
  state = {
    hover: false,
    searchQuery: 'default',
    movieList: []
  };

  toggleHover = () => {
    this.setState({ hover: !this.state.hover });
  };

  handleSearchChange = event => {
    this.setState({ searchQuery: event.target.value });
  };

  handleKeyPress = event => {
    if (event.key === 'Enter') {
      this.props.searchMovie(this.state.searchQuery);
    }
  };

  render() {
    const { searchQuery } = this.state;
    const { classes, searchMovie } = this.props;

    let buttonStyle;
    if (this.state.hover) {
      buttonStyle = { backgroundColor: '#bc0303' };
    } else {
      buttonStyle = { backgroundColor: 'red' };
    }

    return (
      <div className={classes.root}>
        <AppBar position="static" style={divStyle}>
          <Toolbar>
            <span className={'searchBar'}>
              <div className={classes.search}>
                <div className={classes.searchIcon} />
                <InputBase
                  onChange={this.handleSearchChange}
                  onKeyUp={this.handleKeyPress}
                  placeholder="insert movie Title…"
                  classes={{
                    root: classes.inputRoot,
                    input: classes.inputInput
                  }}
                />
              </div>
            </span>
            <Button
              onMouseEnter={this.toggleHover}
              onMouseLeave={this.toggleHover}
              onClick={() => {
                searchMovie(searchQuery);
              }}
              style={buttonStyle}
              variant="contained"
              color="primary"
              className={classes.button}>
              GO
            </Button>
            <Typography
              className={classes.title}
              variant="h6"
              color="inherit"
              noWrap>
              Search for movies
            </Typography>
            <div className={'google-auth'}>
              <GoogleAuth />
            </div>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

ClientSearchAppBar.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(ClientSearchAppBar);
