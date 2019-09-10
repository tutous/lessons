let routeToBuyer: () => Promise<any> = function (): Promise<any> {
    /** security check m.ErrorModule */
    return import('./buyer/buyer.module').then(m => {
        m.BuyerModule;
    });
}
export default routeToBuyer; 