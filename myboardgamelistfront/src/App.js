import './App.css';
import React, {useCallback, useEffect, useRef} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Navbar from "./components/Navbar";
import LogoutPage from "./pages/LogoutPage";
import RegisterPage from "./pages/RegisterPage";
import HomePage from "./pages/HomePage";
import AddGamePage from "./pages/AddGamePage";
import EditGamePage from "./pages/EditPage";
import GameDetailsPage from "./pages/GameDetailsPage";
import GamesByCategoryPage from "./pages/GamesByCategoryPage";
import Footer from "./components/Footer";
import LoginPage from "./pages/LoginPage";

function App() {
  // Get new Token
  const getNewUserToken = async () => {
    const submitUrl = "http://localhost:8080/auth/refresh";
    try {
      const res = await fetch(submitUrl, {
        method: "GET",
        headers: {
          'Authorization': 'Bearer ' + (localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token")),
          "Content-Type": "application/json",
        },
      });

      if (res.status === 200) {
        const data = await res.json();
        if(localStorage.getItem("token") != null)
          localStorage.setItem("token", data.token);
        else
          sessionStorage.setItem("token", data.token);
      } else {
        // New token haven't received.Remove the previous token and user
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("user");
        window.location.reload();
      }
    } catch (err) {
      console.log(err);
    }
  };
  const intervalRef = useRef();
  const getToken = useCallback(() => {
    // Get new token if and only if existing token is available
    if (localStorage.getItem("token") != null || sessionStorage.getItem("token") != null) {
      getNewUserToken();
    }
  }, []);

  // Trigger API to get a new token before token gets expired.
  useEffect(() => {
    const interval = setInterval(() => getToken(), 1000 * 60 * 6); // 6 minutes interval as our token will expire after 7 minutes.
    intervalRef.current = interval;
    return () => clearInterval(interval);
  }, [getToken]);

  const user = () => { return localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token")};

  if(user()){
    getNewUserToken();
  }

  return (
      <div className="App">
        <React.StrictMode>
          <BrowserRouter>
            <Navbar />
            <Routes>
              <Route exact path='/login' element={user() != null ? (<LogoutPage />) : (<LoginPage />)} />
              <Route exact path="/signup" element={user() != null ? (<LogoutPage />) : (<RegisterPage />)} />
              <Route path="/" element={user() != null ? (<HomePage />) : (<LoginPage />)}/>
              <Route path="/games/add" element={user() != null ? (<AddGamePage />) : (<LoginPage />)}/>
              <Route path="/games/:id/update" element={user() != null ? (<EditGamePage />) : (<LoginPage />)}/>
              <Route path="/games/:id/show" element={user() != null ? (<GameDetailsPage />) : (<LoginPage />)}/>
              <Route path="/categories" element={user() != null ? (<GamesByCategoryPage />) : (<LoginPage />)}/>
            </Routes>
          </BrowserRouter>
        </React.StrictMode>
        <Footer />
      </div>
  );
}

export default App;
