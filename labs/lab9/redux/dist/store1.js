"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
exports.decrement = exports.increment = void 0;
exports.demo = demo;
const toolkit_1 = require("@reduxjs/toolkit");
const initialState = 0;
const counterSlice = (0, toolkit_1.createSlice)({
    name: 'counter',
    initialState,
    reducers: {
        increment: (state) => state + 1,
        decrement: (state) => state - 1
    },
});
const store = (0, toolkit_1.configureStore)(counterSlice);
_a = counterSlice.actions, exports.increment = _a.increment, exports.decrement = _a.decrement;
function demo() {
    console.log(store.getState());
    store.dispatch((0, exports.increment)());
    console.log(store.getState());
    store.dispatch((0, exports.increment)());
    console.log(store.getState());
    store.dispatch((0, exports.decrement)());
    console.log(store.getState());
    store.dispatch((0, exports.decrement)());
    console.log(store.getState());
}
exports.default = counterSlice.reducer;
// Demo only
