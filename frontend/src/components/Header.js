import React from 'react';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import Button from '@material-ui/core/Button';
import useMediaQuery from '@material-ui/core/useMediaQuery';
import { withRouter } from "react-router-dom";
import axios from 'axios';
import CreateTopic from './CreateTopic';
import axiosRetry from 'axios-retry';
import ViewTopics from './ViewTopics';

const BASE_URL = "http://localhost:8181/pharmacollab/users/getUserByUserId?userId=Aditya123";
const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1
  },
  menuButton: {
    marginRight: theme.spacing(2)
  },
  title: {
    [theme.breakpoints.down("xs")]: {
      flexGrow: 1
    }
  },
  headerOptions: {
    display: "flex",
    flex: 1,
    justifyContent: "space-evenly"
  }
}));

const Header = props => {
  const [roles, setRoles] = React.useState('');
  const [user, setUser] = React.useState([]);

  const getUsers=()=>{
    localStorage.setItem("userId", "");
    if(Object.keys(user).length===0 && localStorage.getItem("userId") === "") {
      // localStorage.setItem("userId", "");
          axiosRetry(axios, {retries: 0});
    axios.get(BASE_URL).then((response) => {
      console.log(response.data);
      setUser(response.data);
      setRoles(Object.values(response.data.userRoles)[0])
      console.log(roles)
      console.log(Object.values(response.data.userRoles)[0])
      localStorage.setItem('user', JSON.stringify(response.data));
      localStorage.setItem("userId", response.data.userId);
    }, [user])
    .catch(error => {
          console.error("Unable to load data from endpoint:" + BASE_URL, error);
          history.push('/oops');
      });
  }}

  React.useLayoutEffect(() => getUsers())

  const { history } = props;
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("xs"));

  const handleBurgerMenu = event => {
    setAnchorEl(event.currentTarget);
  };

  const handleBurgerMenuClick = pageURL => {
    history.push(pageURL);
    setAnchorEl(null);
  };

  const handleButtonClick = pageURL => {
    history.push(pageURL);
  };

  const burgerMenuItemsWithRoutes = [
    {
      menuTitle: 'Home',
      pageURL: '/'
    },
    {
      menuTitle: 'Create Topic',
      pageURL: '/createTopic'
    },
    {
      menuTitle: 'Allocate Topic',
      pageURL: '/assignTopic'
    },
         {
           menuTitle: 'View Topic',
           pageURL: '/viewTopic'
         },
  ];

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            pamplo
          </Typography>
          {isMobile ? (
            <>
              <IconButton
                edge="start"
                className={classes.menuButton}
                color="inherit"
                aria-label="menu"
                onClick={handleBurgerMenu}
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorEl}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right"
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right"
                }}
                open={open}
                onClose={() => setAnchorEl(null)}
              >
                {burgerMenuItemsWithRoutes.map(burgerMenuItem => {
                  const { menuTitle, pageURL } = burgerMenuItem;
                  return (
                    <MenuItem onClick={() => handleBurgerMenuClick(pageURL)}>
                      {menuTitle}
                    </MenuItem>
                  );
                })}
              </Menu>
            </>
          ) : (
            <div className={classes.headerOptions}>
              <Button
                variant="contained"
                onClick={() => handleButtonClick("/")}
              >
                HOME
              </Button>

              {roles ==='ADMIN' && (
                <>
              <Button
                variant="contained"
                onClick={() => handleButtonClick("/createTopic")}
              >
                Create Topic
              </Button>
              <Button
                variant="contained"
                onClick={() => handleButtonClick("/assignTopic")}
              >
                Allocate Topic
              </Button>
              </>
              )}
              <Button
                variant="contained"
                onClick={() => handleButtonClick("/viewTopic")}
              >
                My Topic/s
              </Button>
            </div>
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
};

export default withRouter(Header);