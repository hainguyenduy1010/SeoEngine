import Vue from 'vue'
import Vuex from 'vuex'

import api from '../backend-api'

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        loginSuccess: false,
        loginError: false,
        userName: null,
        userPass: null
    },
    mutations: {
        login_success(state, payload){
            state.loginSuccess = true;
            state.userName = payload.userName;
            state.userPass = payload.userPass;
        },
        login_error(state, payload){
            state.loginError = true;
            state.userName = payload.userName;
        }
    },
    actions: {
        login({commit}, {username, password}) {
            return new Promise((resolve, reject) => {
                console.log("Accessing backend with user: " + username);
                api.login(username, password)
                    .then(response => {
                        console.log("Response: '" + response.data + "' with Statuscode " + response.status);
                        if(response.status == 200) {
                            // place the loginSuccess state into our vuex store
                            commit('login_success', {
                                userName: username,
                                userPass: password
                            });
                            localStorage.setItem('user', username);
                        }
                        resolve(response)
                    })
                    .catch(error => {
                        console.log("Error: " + error);
                        // place the loginError state into our vuex store
                        commit('login_error', {
                            userName: username
                        });
                        reject("Invalid credentials!")
                    })
            })
        },
        logout() {
            console.log("Logout with user: " + localStorage.getItem('user'));
            localStorage.removeItem('user');
        }
    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserName: state => state.userName,
        getUserPass: state => state.userPass
    }
})