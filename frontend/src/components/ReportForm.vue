<template>
  <form class="form" @submit.prevent="submitForm">
    <div class="form-grid">
      <div>
        <label for="documentoTitulo">Título del documento</label>
        <input id="documentoTitulo" v-model="form.documentoTitulo" type="text" required />
      </div>
      <div>
        <label for="entorno">Entorno</label>
        <input id="entorno" v-model="form.entorno" type="text" required />
      </div>
      <div>
        <label for="plataforma">Plataforma</label>
        <input id="plataforma" v-model="form.plataforma" type="text" required />
      </div>
      <div>
        <label for="funcionalidad">Funcionalidad</label>
        <input id="funcionalidad" v-model="form.funcionalidad" type="text" required />
      </div>
      <div>
        <label for="version">Versión</label>
        <input id="version" v-model="form.version" type="text" required />
      </div>
      <div>
        <label for="destinatario">Destinatario</label>
        <input id="destinatario" v-model="form.destinatario" type="text" required />
      </div>
      <div>
        <label for="fecha">Fecha</label>
        <input id="fecha" v-model="form.fecha" type="date" required />
      </div>
      <div>
        <label for="revision">Revisión</label>
        <input id="revision" v-model="form.revision" type="text" required />
      </div>
      <div>
        <label for="cliente">Cliente</label>
        <input id="cliente" v-model="form.cliente" type="text" required />
      </div>
    </div>
    <button type="submit" :disabled="loading">{{ loading ? 'Generando...' : 'Generar informe' }}</button>
    <div v-if="message" class="alert" :class="message.type">
      {{ message.text }}
    </div>
  </form>
</template>

<script setup>
import axios from 'axios';
import { reactive, ref } from 'vue';

const form = reactive({
  documentoTitulo: 'DOCUMENTO DE INSTALACIÓN',
  entorno: 'PRODUCCIÓN',
  plataforma: 'PCM',
  funcionalidad: 'Alarmado Tráfico Internacional',
  version: '5.4.0',
  destinatario: 'Telefónica Móviles España',
  fecha: '2025-11-10',
  revision: '1.0',
  cliente: 'TME'
});

const loading = ref(false);
const message = ref(null);

const resetMessage = () => {
  message.value = null;
};

const submitForm = async () => {
  resetMessage();
  loading.value = true;
  try {
    const response = await axios.post(
      '/api/report',
      {
        ...form
      },
      {
        responseType: 'blob'
      }
    );

    const blob = new Blob([response.data], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'informe.pdf');
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);

    message.value = { type: 'success', text: 'Informe generado correctamente.' };
  } catch (error) {
    message.value = { type: 'error', text: 'No se pudo generar el informe. Revisa los datos o inténtalo de nuevo.' };
  } finally {
    loading.value = false;
  }
};
</script>
