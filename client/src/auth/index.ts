const Auth = {
    isAuthenticated: function() {
        return localStorage.getItem('isLogged') === 'true';
    }
}

export default Auth