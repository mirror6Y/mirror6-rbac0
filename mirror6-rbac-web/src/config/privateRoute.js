import {
    Route,
    Redirect
} from 'react-router-dom';
import React, { Component } from 'react';
// import { connect } from 'react-redux';
import storageUtil from '../utils/storageUtil'


class PrivateRoute extends Component {

    // constructor(props) {
    //     super(props);
    //     console.log("13:"+JSON.stringify(this.props.path))
    //     return;
    // }

    componentDidMount() {
        const user = storageUtil.getUser();
        console.log("存放的user:"+user)
        console.log("1:"+this.props.path)
    }

    render() {

        // const user = storageUtil.getUser;
        // console.log(JSON.stringify(user))
        // console.log("1:"+this.props.path)

        // let { log, location, ...other } = this.props;
        let component = null != "1" ?
        <Redirect to="/home" /> :
            <Redirect to="/home" />

        return component;
    }
}

// const mapStateToProps = (state) => {
//     return state.log
// }

export default PrivateRoute