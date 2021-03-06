import React, { Component } from 'react';
import { message } from 'antd';
import { connect } from 'react-redux';
import { Role } from '../../model/user';
import { OrderState } from '../../model/order';
import { logout } from '../../redux/auth/actions';
import { route } from '../../redux/ui/actions';
import { RouteTable } from '../../route';

class PopoverContent extends Component {
    onLogoutClick = () => {
        this.props.logout();
        this.props.route(RouteTable.CUSTOMER.Main.path, this.props.isAuthed);
        message.success('登出成功');
    };

    render() {
        return this.props.user.role === Role.CUSTOMER ? (
            <div>
                <p>已经消费： {(this.props.user.paid / 100).toFixed(2)}元</p>
                <p>获取积分： {this.props.user.point}</p>
                <p>
                    已经就绪的订单数：{
                        this.props.orders.filter(
                            o => o.orderState === OrderState.READY
                        ).length
                    }
                </p>
                <a onClick={this.onLogoutClick}>退出登录</a>
            </div>
        ) : (
                <div>
                    <a onClick={this.onLogoutClick}>退出登录</a>
                </div>
            );
    }
}

const mapStateToProps = state => {
    return {
        user: state.loginReducer.user,
        orders: state.orderReducer.orders,
        isAuthed: state.loginReducer.isAuthed
    };
};

const mapDispatchToProps = dispatch => {
    return {
        logout: () => dispatch(logout()),
        route: (key, isAuthed) => dispatch(route(key, isAuthed))
    };
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(PopoverContent);
