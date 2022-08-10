<script>
    import {getTodayDate} from "../helper/iddate.js"

    export let namaHalaman
    export let icon
    export let marginBottom = 84
    export let btnTambah = () => {}
    export let btnPlus = false

  
</script>

 <div style="display: flex; align-items:center;height: 65px; margin-bottom: {marginBottom}px; justify-content: space-between">
    <div style="display:flex; align-items:center"> 
        <img src="./icons/{icon}.png" alt="home dashboard" style="height: 30px;" >
        <p class="tops">{namaHalaman}</p>
        {#if btnPlus}
        <img src="./icons/plus_button.png" alt="tambah" style="margin-left:16px;"
        on:click={btnTambah}/>
        {/if}
    </div>
    <p class="tops" style="margin-right: 20px;">{getTodayDate()}</p>
</div>


<style>
    .tops{
        font-size: 28px; 
        color: var(--accent-color);
    }
</style>
