export interface OrderRequestInterface {
  customerName: string;
  customerEmail: string;
  items: { wineId: number; quantity: number }[];
}

export interface OrderInterface {
  id: number;
  customerName: string;
  customerEmail: string;
  createdAt: string;
  total: number;
  items: { wineId: number; wineName: string; quantity: number; unitPrice: number }[];
}
