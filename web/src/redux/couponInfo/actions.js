import {COUPON_INFO_FIND_ALL_BY_USER_ID} from './types'
import axios from "axios";
import {Api} from '../../api'

export function findAllByUserId(userId) {
    return dispatch => {
        axios.get(Api.couponInfo.findAllByUserId, {
            params: {"userId": userId},
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then(resp => {
            if (resp.data) dispatch({type: COUPON_INFO_FIND_ALL_BY_USER_ID, couponInfos: resp.data.value})
        })
    }
}