/**
 * 
 */
const USER_KEY = 'user';
const Token='token';

export default {
    setUser(user) {
        localStorage.setItem(USER_KEY, JSON.stringify(user));
    },

    getUser() {
        return JSON.parse(localStorage.getItem(USER_KEY));
    },

    removeUser() {
        localStorage.removeItem(USER_KEY);
    },

    //token
    setToken(token){
           localStorage.setItem(Token, token);
    },

    getToken() {
            return localStorage.getItem(Token);
    },

    removeToken() {
            localStorage.removeItem(Token);
    },

}