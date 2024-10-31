import logo from "./logo.svg";
import "./App.css";
import notFoundURL from "./pages/404error";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import MyContact from "./config/MyContact.js";
import { publicRoutes,privateRoutes } from "./routes/routes";
import ProtectedRoute from "./routes/protectedRoute.js";
import { Fragment } from "react";
import { HomeLayouts } from "./layouts";
import MyUserReducer from "./reducers/MyUserReducer.js";
import MyContext from "./config/MyContext.js";
import MyRequest from "./config/MyRequest.js";
import React, { useReducer, useState } from "react";
function App() {
  const [contacts, setContacts] = useState([]);
  const [request, setRequests] = useState([]);
  const [user, dispatch] = useReducer(MyUserReducer, null);
  return (
    <MyContext.Provider value={[user, dispatch]}>
      <MyContact.Provider value={[contacts, setContacts]}>
      <MyRequest.Provider value={[request, setRequests]}>
        <Router>
          <div className="App">
            <Routes>
              {publicRoutes.map((route, index) => {
                const Page = route.component;
                let Layout = route.layout || Fragment;
                return (
                  <Route
                    key={index}
                    path={route.path}
                    element={
                      <Layout>
                        <Page />
                      </Layout>
                    }
                  />
                );
              })}
              
              {privateRoutes.map((route, index) => {
                const Page = route.component;
                let Layout = route.layout || Fragment;
                return (
                  <Route
                    key={index}
                    path={route.path}
                    element={
                      <ProtectedRoute>
                        <Layout>
                          <Page />
                        </Layout>
                      </ProtectedRoute>
                    }
                  />
                );
              })}

              <Route path="*" element={<notFoundURL />} />
            </Routes>
          </div>
        </Router>
        </MyRequest.Provider>
      </MyContact.Provider>
    </MyContext.Provider>
  );
}

export default App;
