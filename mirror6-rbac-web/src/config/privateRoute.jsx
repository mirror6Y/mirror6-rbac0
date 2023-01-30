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

    // componentDidMount() {
    //     const user = storageUtil.getUser();
    //     const rol = storageUtil.getUser().rol;
    //     const path=""+this.props.path;
    //     console.log("存放的user:"+JSON.stringify( user))
    //     console.log("存放的user url:"+JSON.stringify( rol))
    //     console.log("菜单url:"+path)
    //             this.setState({user:user,path:path})
    //
    // }

    render() {
        const { component: Component, ...rest } = this.props;
        // const rol = storageUtil.getUser().rol;
        const path=""+this.props.path;
        const res=path.indexOf(path);
        // console.log("rol:"+JSON.stringify( rol))
        console.log("菜单url:"+path)
        console.log("res:"+res)

        return (
                <Route {...rest} render={props => {
                    return res!==-1
                            ?  <Component {...props} />
                            : <Redirect to="/login" />
                }} />
        )
    }

    // render() {
    //
    //     // const user = storageUtil.getUser;
    //     // console.log(JSON.stringify(user))
    //     // console.log("1:"+this.props.path)
    //
    //
    //
    //     // let { log, location, ...other } = this.props;
    //     let component = res>0  ?
    //     <Redirect to="/home" /> :
    //         <Redirect to="/home" />
    //
    //     return component;
    // }
}

// const mapStateToProps = (state) => {
//     return state.log
// }

export default PrivateRoute;