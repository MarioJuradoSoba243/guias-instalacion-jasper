<template>
  <main class="layout">
    <header class="hero">
      <h1>Documento de instalación</h1>
      <p>Rellena los datos de portada para generar un PDF similar al modelo de referencia.</p>
    </header>
    <section class="card">
      <ReportForm />
    </section>
  </main>
</template>

<script setup>
import ReportForm from './components/ReportForm.vue';
</script>
