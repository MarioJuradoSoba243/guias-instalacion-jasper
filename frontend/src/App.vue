<template>
  <main class="layout">
    <header class="hero">
      <h1>Codex – Generador de informe Jasper</h1>
      <p>Completa los datos y descarga un PDF generado en el backend.</p>
    </header>
    <section class="card">
      <ReportForm />
    </section>
  </main>
</template>

<script setup>
import ReportForm from './components/ReportForm.vue';
</script>
