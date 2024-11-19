"use strict";
var _a;
Object.defineProperty(exports, "__esModule", { value: true });
exports.setTimeZone = exports.tick = void 0;
exports.demo = demo;
const toolkit_1 = require("@reduxjs/toolkit");
const initialState = { time: new Date().toLocaleString("pt-PT", { timeZone: "America/New_York" }), timezone: "America/New_York" };
const counterSlice = (0, toolkit_1.createSlice)({
    name: 'counter',
    initialState,
    reducers: {
        tick: (state) => { state.time = new Date().toLocaleString("pt-PT", { timeZone: state.timezone }); },
        setTimeZone: (state, action) => { state.timezone = action.payload; }
    },
});
const store = (0, toolkit_1.configureStore)(counterSlice);
_a = counterSlice.actions, exports.tick = _a.tick, exports.setTimeZone = _a.setTimeZone;
exports.default = counterSlice.reducer;
// Demo only
function displayClock() {
    const { time } = store.getState();
    console.log(time);
}
function demo() {
    displayClock();
    store.dispatch((0, exports.setTimeZone)("Europe/Lisbon"));
    store.dispatch((0, exports.tick)());
    displayClock();
    store.dispatch((0, exports.setTimeZone)("Europe/Paris"));
    store.dispatch((0, exports.tick)());
    displayClock();
    store.dispatch((0, exports.setTimeZone)("Asia/Tokyo"));
    store.dispatch((0, exports.tick)());
    displayClock();
}
