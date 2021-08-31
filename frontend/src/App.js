import React from 'react';
import './App.css';
import AppLoader from './components/AppLoader';
import Header from './components/Header';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles({});
export default function App() {
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Header />
      <AppLoader pageWrapId={'page-wrap'} outerContainerId={'outer-container'} name={'P.A.M.PLO'} />
    </div>
  );
}