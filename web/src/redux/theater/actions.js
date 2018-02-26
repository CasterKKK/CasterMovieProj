import {SELECT_THEATER} from "./types";
import axios from "axios";
import {Api} from '../../api'

export const selectTheater = (id) =>
    dispatch =>
        axios.get(Api.theater.findById, {
            params: {"id": id},
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then(resp => {
            if (resp.data) dispatch({type: SELECT_THEATER, selectedTheater: resp.data.value})
        });
