import { createApp } from 'vue';
import { createPinia } from 'pinia';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { fab } from '@fortawesome/free-brands-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { library } from '@fortawesome/fontawesome-svg-core';
import piniaPluginPersistedState from 'pinia-plugin-persistedstate';

import './styles/base.scss';

import App from './App.vue';
import router from './router';

const app = createApp(App);
library.add(fas, fab);
app.component('font-awesome-icon', FontAwesomeIcon);

const pinia = createPinia();
pinia.use(piniaPluginPersistedState);
app.use(pinia);
app.use(router);

app.mount('#app');
