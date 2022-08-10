<script>
     import {apiurl} from '../helper/apiurl.js'
    import Topbar from '../components/Topbar.svelte'
    import Table from '../components/Table.svelte'
    import DetailTable from '../components/detailtables/DetailTableTeknisi.svelte'
    import FDM from '../components/forms/form_data_teknisi.svelte'

    let id
    let detailDilihat = false
    let editDetail = false
    let tambahDetail = false
    let tableHead = ['id',
                    'Nama',
                    'Alamat',
                    'Keahlian',
                    'Proses'
                        ]
    let detailPane 
    let dtblCFG ={
                "ajax":{
                        "url": apiurl+"daftarteknisi",
                        "type": "GET",
                        "beforeSend": function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        }
                        
                    },
                columns: [
                        { data: 'id' },
                        { data: 'nama' },
                        { data: 'alamat' },
                        { data: 'keahlian' },
                        { data: 'jml_diproses' }
                        
         
                    ],
                "columnDefs": [ 
                    {
                        "targets": -1,
                        "render" : function ( data, type, row ) {
                            return "<div class='acw' style='display:flex;justify-content:space-between'>" + data + " <img src='./icons/table_menu.svg' class='cxbtn' style='height:18px; width: 20px '/> </div>"
                        }
                    },
                    // field 1 isinya id, di hide
                    {
                        "targets": 0,
                        "visible" : false
                    } ,
                    {
                        "targets": 1,
                        "render" : function ( data, type, row ) {
                            return data.length > 14 ? data.substring(0,13)+"..." : data
                        },
                    } ,
                    {
                        "targets": 2,
                        "render" : function ( data, type, row ) {
                            return data.length > 8 ? data.substring(0,7)+"..." : data
                        },
                    
                    } ,
                    // {
                    //     "targets": 4,
                    //     "render" : function ( data, type, row ) {
                    //         return data.length > 8 ? data.substring(0,7)+"..." : data
                    //     },
                    // } ,
                   
                ]
            }
            function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }
   function detail(diapain) {
       switch (diapain) {
           case 'tutup':
                detailDilihat = false       
               break;
            case 'buka':
                detailDilihat = true
                detailPane = 'detail'
               break;
       }
    }
   function edit(diapain) {
       switch (diapain) {
           case 'tutup':
                editDetail = false       
               break;
            case 'buka':
                editDetail = true
                detailPane = 'edit'
               break;
       }
    }

    function tambah (diapain){
        switch (diapain) {
           case 'tutup':
                tambahDetail = false       
               break;
            case 'buka':
                tambahDetail = true
                detailPane = 'tambah'
               break;
       }
    }
</script>

<div style="display: block; width: 100%; padding-left: 35px">

    <Topbar btnPlus icon="teknisi" namaHalaman="Daftar Teknisi" marginBottom="40" btnTambah={() => tambah('buka')}/>

    <div style="display: flex; gap:100px; width: 100%;" class="boddy">
        <Table lihatDetail = {() => detail('buka')} editDetail = { () => edit('buka')}  bind:id tableHead={tableHead} dataTableCFG={dtblCFG}  context='Teknisi'/>
        {#if detailDilihat && detailPane === 'detail'}
        <div style="position: relative; width: 500px">
            <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={() => detail('tutup')}/>
            <DetailTable id={id} />
        </div>
        {:else if editDetail && detailPane === 'edit'}
          <FDM  removeMe={() => edit('tutup')} purpose='edit' formCount=1 formID={id}/>
            
        {:else if tambahDetail && detailPane === 'tambah'}
          <FDM  removeMe={() => tambah('tutup')} purpose='tambah' formCount=1/>
        {/if}
    </div>
</div>




<style>
    @media only screen and (max-width: 900px){
        .boddy{
            flex-direction: column;
        }
    }
</style>