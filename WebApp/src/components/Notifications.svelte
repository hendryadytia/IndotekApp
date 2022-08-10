<script>
    export let isinotif,jamMasuk,onclick
</script>


<div style="display: flex; width:100%; align-items: center; margin-bottom: 10px" on:click={onclick}>
    <p style="background-color: var(--accent-color); padding:4px 8px; border-radius: 10px; color: white; font-size:12px">{jamMasuk}</p>
    <p style="margin: 0 10px;">{isinotif}</p>
</div>