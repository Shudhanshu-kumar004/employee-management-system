export interface Leaves {
  id: number;
  userId: number;
  userName: string;
  reason: string;
  fromDate: string;
  toDate: string;
  appliesAt: string;
  approvedAt: string | null;
  status: string;
}