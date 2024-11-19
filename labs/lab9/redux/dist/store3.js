"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.demo = demo;
const toolkit_1 = require("@reduxjs/toolkit");
const store1_1 = require("./store1");
const store2_1 = require("./store2");
const store = (0, toolkit_1.configureStore)({ reducer: { counter: store1_1.default, clock: store2_1.default } });
function demo() {
    console.log(store.getState());
    store.dispatch((0, store1_1.increment)());
    console.log(store.getState());
    store.dispatch((0, store2_1.setTimeZone)("Europe/Paris"));
    store.dispatch((0, store2_1.tick)());
    console.log(store.getState());
}
