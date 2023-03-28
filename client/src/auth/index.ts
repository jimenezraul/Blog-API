interface UserAuth {
  isLogged: boolean;
  id: number;
  isAdmin: boolean;
}

const Auth = {
  getUser: function (): UserAuth | null {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  isAuthenticated: function (): boolean {
    const user = this.getUser();
    return Boolean(user && user.isLogged);
  },

  isAdmin: function (): boolean {
    const user = this.getUser();
    return Boolean(user && user.isAdmin);
  },

  getUserId: function (): number | null {
    const user = this.getUser();
    return user ? user.id : null;
  },
  logout: function (): void {
    localStorage.removeItem('user');
  },
};

export default Auth;
