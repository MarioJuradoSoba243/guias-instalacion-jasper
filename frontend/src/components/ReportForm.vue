<template>
  <form class="form" @submit.prevent="submitForm">
    <div class="form-grid">
      <div>
        <label for="nombre">Nombre del cliente</label>
        <input id="nombre" v-model="form.nombreCliente" type="text" required placeholder="Juan Pérez" />
      </div>
      <div>
        <label for="fecha">Fecha del informe</label>
        <input id="fecha" v-model="form.fechaInforme" type="date" required />
      </div>
      <div>
        <label for="importe">Importe total</label>
        <input id="importe" v-model="form.importeTotal" type="number" step="0.01" min="0" required placeholder="0.00" />
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
  nombreCliente: '',
  fechaInforme: '',
  importeTotal: ''
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
    const response = await axios.post('/api/report', {
      ...form,
      importeTotal: form.importeTotal ? Number(form.importeTotal) : null
    }, {
      responseType: 'blob'
    });

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
