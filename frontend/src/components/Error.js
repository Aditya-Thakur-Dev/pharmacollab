import React from 'react'
import oops from './../resources/oops.jpg'
import { makeStyles } from '@material-ui/core/styles';
import PetsIcon from '@material-ui/icons/Pets';

const Error = props => {
    const useStyles = makeStyles(theme => ({
        headerOptions: {
          display: "flex",
          flex: 1,
          justifyContent: "space-evenly"
        },
        div: {
            position: 'relative',
            marginRight: theme.spacing(10),
            marginLeft: theme.spacing(10),
            marginTop: theme.spacing(5),
            display: 'flex',
            justifyContent: 'center',
        }
      }));
    
      const classes = useStyles();

      return (
        <div className={classes.div}>
            <h1>Oops! That was unexpected. Please <PetsIcon/> with our prototype</h1>
            <img src={oops} alt="That wasn't supposed to happen"/>
        </div>
    )
}

export default Error