import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {BrowserRouter, Route, Routes, useLocation, useNavigate,} from "react-router-dom";
import {utils} from "./utils/_helper.tsx";

const Root = () => {
    utils.navigate = useNavigate();
    utils.location = useLocation();
    return (
        <Routes>

            <Route path="/*" element={<App/>}/>
        </Routes>
    );
};




ReactDOM.createRoot(document.getElementById("root")!).render(
    <BrowserRouter>
        <Root/>
    </BrowserRouter>
);
