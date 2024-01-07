import Axios from "axios";



const REACT_APP_API_URL = "http://localhost:8080/api";

export const getCompetitions = async () => {
    return await Axios.get(`${REACT_APP_API_URL}/competitions`);
}