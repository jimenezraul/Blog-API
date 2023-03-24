/// <reference types="react-scripts" />

interface LoginForm {
    username: string;
    password: string;
    error: {
        username: string;
        password: string;
    }
}