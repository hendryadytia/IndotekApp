<script>
    import Card from './Card.svelte'
    import Input from '../components/inputText.svelte'
    import ButtonSmall from './ButtonSmall.svelte';
    import X from 'jquery'
    import { onMount } from 'svelte'

    export let visibility = false
    export let visibilitytombolkonfirm = true
    export let konfirmasi = false
    export let judul = "Konfirmasi Password"
    export let width = 300
    export let height
    export let titleLanjutbtn = "hapus"
    let pswd

    export let jawab = (konfirm)=>{
        if (konfirm == 'lanjut') {    
           konfirmasi = true
           visibility = false
        }else{
            visibility = false
        }
    }

    const close = () => {
        visibility=false;
    }

    onMount(()=> {
        X('.overlay-confirm').on('click', function(e){
                if(e.target == e.currentTarget) {
                    pswd = ''
                    visibility = false
                }
            })
    })
   
</script>
{#if visibility}
  <div style="display:flex; width: 100vw; position: absolute; top:0; left:0; bottom:0; background-color: rgba(0, 0, 0, 0.363);  align-items: center; justify-content: center; z-index: 30" class="overlay-confirm">
    <Card width={width} height={height} useShadow style="position:relative">
        <div style="display: flex; flex-direction: column; justify-content:flex-start; height: inherit; justifiy-content: flex-end; gap: 20px"> 
            <Card width={width} useShadow style="margin-top: -10px;margin-left:-10px; top:0; position: absolute">
                <h1 style="margin-bottom: auto; color: var(--accent-color)">{judul}</h1>
            </Card>
              
            <div style="margin-top: 35px"> <slot/></div>
            <div style="display: flex; justify-content: space-between">
                <ButtonSmall on:click={() => close()}> Batal </ButtonSmall>
                {#if visibilitytombolkonfirm}
                    
                <ButtonSmall on:click={()=>jawab('lanjut')}> {titleLanjutbtn} </ButtonSmall>
                {/if}
            </div>
            
        </div>
    </Card>
    </div>
{/if}
